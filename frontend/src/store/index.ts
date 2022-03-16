import { createStore } from 'vuex'
import { createProxy, extractVuexModule } from "vuex-class-component";
import { SignStore } from "@/store/sign";
import { KeysStore } from "@/store/keys";
import { VerifyStore } from "@/store/verify";

export const store =  createStore({
  modules: {
    ...extractVuexModule(KeysStore),
    ...extractVuexModule(SignStore),
    ...extractVuexModule(VerifyStore)
  }
})

export const vxm = {
  keys: createProxy( store, KeysStore ),
  sign: createProxy( store, SignStore ),
  verify: createProxy( store, VerifyStore )
}

export default store;
