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
        process.env.VUE_APP_BACKEND_URL + '/sign-file',
        formData,
        {
          headers: {
            'Content-Type': 'multipart/form-data',
            accept: 'application/json',
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
}
