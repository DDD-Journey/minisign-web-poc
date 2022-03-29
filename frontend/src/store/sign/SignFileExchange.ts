import axios from 'axios';
import MinisignResponse from '@/store/share/domain/MinisignResponse';

export default class SignFileExchange {
  static async callSignFileApi(
    documentFile: File,
    secretKeyFile: File,
    password: string,
    signatureFileName: string
  ): Promise<MinisignResponse | undefined> {
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
        console.log('Axios Error', error);
      } else {
        console.log(error);
      }
    }
  }

  static async callDownloadSignatureFileApi(
    sessionId: string
  ): Promise<any | undefined> {
    try {
      const response = await axios.get(
          process.env.BACKEND_URL + '/download-files/' + sessionId,
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
        console.log('Axios Error', error);
      } else {
        console.log(error);
      }
    }
  }
}
