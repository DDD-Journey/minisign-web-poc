import { createModule, action, getter, mutation } from "vuex-class-component";
import axios, { AxiosInstance } from "axios";


const VuexModule = createModule({
    namespaced: "sign",
    strict: false
})

export class SignStore extends VuexModule {
    private files!: FileList;
    private password = "";

    @action
    async signFile() {
        if (this.files){
            console.log(this.password)
            console.log(this.files)
            const uploadData = new FormData();
            uploadData.append('file', this.files[0]);
            try {
               const response =  await axios.post('http://localhost:3000/upload', uploadData, {
                        headers: {
                            'Content-Type': 'undefined'
                        }
                })
                console.log(response)
            } catch (error) {
                if (axios.isAxiosError(error)) {
                    console.log(error)
                } else {
                    console.log(error)
                }
            }
        }
        // Todo something
        // Call Backend
        // Resturn signed file
    }
}
