import { createStore } from 'vuex'
import { createProxy, extractVuexModule } from "vuex-class-component";
import { SignStore } from "@/store/sign";
import { KeysStore } from "@/store/keys";

export const store =  createStore({
  modules: {
    ...extractVuexModule(KeysStore),
    ...extractVuexModule(SignStore)
  }
})

export const vxm = {
  keys: createProxy( store, KeysStore ),
  sign: createProxy( store, SignStore )
}

export default store;