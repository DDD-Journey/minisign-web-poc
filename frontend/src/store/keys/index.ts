import { createModule, action } from "vuex-class-component";
import password from "@/components/Password.vue";

const VuexModule = createModule({
    namespaced: "keys",
    strict: false
})

export class KeysStore extends VuexModule {
    private password = "";

    get getPassword() {
        return this.password;
    }

    @action
    async createPrivateKeys() {
        console.log(`Store: ${this.password}`)
        // Todo something
        // Call Backend
        // Return a zip file with a private and public key
    }
}
