import { createModule, action } from 'vuex-class-component';
import axios from 'axios';
import { downloadFile } from '@/store/share/downloadFile';

const VuexModule = createModule({
  namespaced: 'sign',
  strict: false,
});

export class SignStore extends VuexModule {
  private files!: FileList;
  private password = '';

  @action
  async signFile() {
    if (this.files) {
      console.log(this.password);
      console.log(this.files);
      const formData = new FormData();
      for (let i = 0; i < this.files.length; i++) {
        formData.append(this.files[i].name, this.files[i]);
      }
      formData.append('password', this.password);
      try {
        const response = await axios.post(
          'http://localhost:3000/sign',
          formData,
          {
            headers: {
              'Content-Type': 'undefined',
            },
            responseType: 'blob',
          }
        );
        downloadFile(response.data, `minisign-signed.zip`);
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
