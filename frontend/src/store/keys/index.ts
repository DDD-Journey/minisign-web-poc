import { createModule, action } from "vuex-class-component";

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
        // Todo something
        // Call Backend
        // Return a zip file with a private and public key
    }
}