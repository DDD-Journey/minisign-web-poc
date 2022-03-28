import { createModule, action } from 'vuex-class-component';
import { downloadFile } from '@/store/share/downloadFile';
import SignFileExchange from '@/store/sign/SignFileExchange';

const VuexModule = createModule({
  namespaced: 'sign',
  strict: false,
});

export class SignStore extends VuexModule {
  private documentFiles!: FileList;
  private secretKeyFiles!: FileList;
  private password = '';
  private errorMessage = '';

  @action
  async signFile() {
    if (this.documentFiles && this.secretKeyFiles) {
      console.log(`password: ${this.password}`);
      console.log(`documentFile: `);
      console.log(this.documentFiles[0]);
      console.log(`secretKeyFile: `);
      console.log(this.secretKeyFiles[0]);

      const signatureFileName = this.documentFiles[0].name + '.minisig';
      try {
        const signFileResponse = await SignFileExchange.callSignFileApi(
            this.documentFiles[0],
            this.secretKeyFiles[0],
            this.password,
            signatureFileName
        );
        console.log('signFileResponse');
        console.log(signFileResponse);
        const sessionId =
            signFileResponse != undefined ? signFileResponse.sessionId : undefined;
        if (sessionId) {
          const downloadFilesResponse =
              await SignFileExchange.callDownloadSignatureFileApi(sessionId);
          console.log('DownloadFilesResponse');
          console.log(downloadFilesResponse);
          downloadFile(downloadFilesResponse, signatureFileName + '.zip');
        }
      } catch (error) {
        console.log(error);
        this.errorMessage = 'Something went wrong, try again later.';
      }
    }
  }
}
