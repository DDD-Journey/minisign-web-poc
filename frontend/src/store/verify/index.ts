import { createModule, action } from 'vuex-class-component';
import VerifyFileExchange from '@/store/verify/VerifyFileExchange';

const VuexModule = createModule({
  namespaced: 'verify',
  strict: false,
});

export class VerifyStore extends VuexModule {
  private documentFiles!: FileList;
  private signatureFiles!: FileList;
  private publicKeyFiles!: FileList;
  private isVerified = false;
  private verificationResult = '';
  private errorMessage = '';

  @action
  async verifyFile() {
    if (this.documentFiles && this.signatureFiles && this.publicKeyFiles) {
      try {
        const verifyFileResponse = await VerifyFileExchange.calLVerifyFileApi(
          this.documentFiles[0],
          this.signatureFiles[0],
          this.publicKeyFiles[0]
        );
        if (verifyFileResponse) {
          this.verificationResult = verifyFileResponse.processFeedback;
          this.isVerified = true;
        }
      } catch (error) {
        console.log(error);
        this.errorMessage = 'Something went wrong, try again later.';
      }
    }
  }
}
