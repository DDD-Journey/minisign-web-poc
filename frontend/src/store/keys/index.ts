import { createModule, action } from 'vuex-class-component';
import CreateKeyPairExchange from '@/store/keys/CreateKeyPairExchange';
import SignFileExchange from '@/store/sign/SignFileExchange';
import { downloadFile } from '@/store/share/downloadFile';
import DownloadExchange from "@/store/share/DownloadExchange";

const VuexModule = createModule({
  namespaced: 'keys',
  strict: false,
});

export class KeysStore extends VuexModule {
  private password = '';
  private fileName = '';
  private errorMessage = '';

  @action
  async createPrivateKeys() {
    console.log(`Store: ${this.password}`);
    if (this.password && this.fileName) {
      try {
        const signFileResponse =
          await CreateKeyPairExchange.callCreateKeyPairApi(
            this.password,
            this.fileName
          );
        const sessionId =
          signFileResponse != undefined
            ? signFileResponse.sessionId
            : undefined;
        if (sessionId) {
          const downloadFilesResponse =
            await DownloadExchange.callDownloadSignatureFileApi(sessionId);
          console.log('DownloadFilesResponse');
          console.log(downloadFilesResponse);
          downloadFile(downloadFilesResponse, this.fileName + '.zip');
        }
      } catch (error) {
        console.log(error);
        this.errorMessage = 'Something went wrong, try again later.';
      }
    }
  }
}
