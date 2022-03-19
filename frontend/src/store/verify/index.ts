import { createModule, action } from 'vuex-class-component';
import axios from 'axios';

const VuexModule = createModule({
  namespaced: 'verify',
  strict: false,
});

export class VerifyStore extends VuexModule {
  private files!: FileList;
  private isVerified = false;
  private verificationResult = '';

  @action
  async verifyFile() {
    if (this.files) {
      console.log(this.files);
      const formData = new FormData();
      for (let i = 0; i < this.files.length; i++) {
        formData.append(this.files[i].name, this.files[i])
      }
      try {
        const response = await axios.post(
          'http://localhost:3000/verify',
            formData,
          {
            headers: {
              'Content-Type': 'undefined',
            },
          }
        );
        this.verificationResult = response.data.message;
        this.isVerified = true;
        console.log(response);
      } catch (error) {
        if (axios.isAxiosError(error)) {
          console.log(error);
        } else {
          console.log(error);
        }
      }
    }
  }
}
