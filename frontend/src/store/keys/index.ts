import { createModule, action } from 'vuex-class-component';
import password from '@/components/Password.vue';
import axios from 'axios';

const VuexModule = createModule({
  namespaced: 'keys',
  strict: false,
});

export class KeysStore extends VuexModule {
  private password = '';

  @action
  async createPrivateKeys() {
    console.log(`Store: ${this.password}`);
    if (this.password) {
      try {
        await axios.post(
          'http://localhost:3000/create',
          {
            password: this.password,
          },
          {
            headers: {
              'Content-Type': 'application/json',
            },
          }
        );
      } catch (error) {
        if (axios.isAxiosError(error)) {
          console.log(error);
        } else {
          console.log(error);
        }
      }
    }
    // Todo something
    // Call Backend
    // Return a zip file with a private and public key
  }
}
