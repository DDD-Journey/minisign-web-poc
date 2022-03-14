import { createStore } from 'vuex'
import { createProxy, extractVuexModule } from "vuex-class-component";
import { KeysStore } from "@/store/keys";

export const store =  createStore({
  modules: {
    ...extractVuexModule(KeysStore)
  }
})

export const vxm = {
  keys: createProxy( store, KeysStore )
}

export default store;