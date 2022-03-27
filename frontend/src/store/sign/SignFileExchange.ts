import axios from 'axios';
import SignFileResponse from '@/store/sign/domain/SignFileResponse';

export default class SignFileExchange {

  static async callSignFileApi(
    documentFile: File,
    secretKeyFile: File,
    password: string,
    signatureFileName: string
  ): Promise<SignFileResponse | undefined> {
    const formData = new FormData();
    formData.append('unsigned-file', documentFile);
    formData.append('secret-key-file', secretKeyFile);
    try {
      const response = await axios.post(
        'http://localhost:8080/sign-file',
        formData,
        {
          headers: {
            'Content-Type': 'undefined',
            accept: '*/*',
          },
          params: {
            password: password,
            'signature-file-name': signatureFileName,
          },
        }
      );
      if (!response) {
        throw new Error('Sign File API unavailable.');
      }
      console.log('callSignFileApi');
      console.log(response);
      return response.data;
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.log(error);
      } else {
        console.log(error);
      }
    }
  }

  static async callDownloadSignatureFileApi(sessionId: string): Promise<any | undefined> {
    try {
      const response = await axios.get(
        'http://localhost:8080/download-files/' + sessionId,
        {
          headers: {
            accept: 'application/zip',
          },
          responseType: 'blob',
        }
      );
      if (!response) {
        throw new Error('Download Files API unavailable.');
      }
      console.log('callDownloadSignatureFileApi');
      console.log(response);
      return response.data;
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.log(error);
      } else {
        console.log(error);
      }
    }
  }
}
