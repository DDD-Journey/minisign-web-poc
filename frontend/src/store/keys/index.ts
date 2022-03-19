import { createModule, action } from 'vuex-class-component';
import axios from 'axios';
import { downloadFile } from '@/store/share/downloadFile';

const VuexModule = createModule({
  namespaced: 'keys',
  strict: false,
});

export class KeysStore extends VuexModule {
  private password = '';
  private isSuccess = false;
  private serverResponse = '';

  @action
  async createPrivateKeys() {
    console.log(`Store: ${this.password}`);
    if (this.password) {
      try {
        const response = await axios.post(
          'http://localhost:3000/create',
          {
            password: this.password,
          },
          {
            headers: {
              'Content-Type': 'application/json',
            },
            responseType: 'blob',
          }
        );
        console.log(response.data);
        this.isSuccess = true;
        this.serverResponse = response.data;
        downloadFile(response.data, 'minisign-keys.zip');
        2;
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
