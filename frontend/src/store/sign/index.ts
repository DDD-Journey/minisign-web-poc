import { createModule, action, getter, mutation } from "vuex-class-component";

const VuexModule = createModule({
    namespaced: "sign",
    strict: false
})

export class SignStore extends VuexModule {
    private files!: FileList;

    @action
    async signFile() {
        if (this.files){
            console.log(this.files)
        }
        // Todo something
        // Call Backend
        // Return a zip file with a private and public key
    }
}