import axios from 'axios';
import MinisignResponse from '@/store/share/domain/MinisignResponse';

export default class VerifyFileExchange {
  static async calLVerifyFileApi(
    documentFile: File,
    signatureFiles: File,
    publicKeyFile: File
  ): Promise<MinisignResponse | undefined> {
    const formData = new FormData();
    formData.append('signed-file', documentFile);
    formData.append('signature-file', signatureFiles);
    formData.append('public-key-file', publicKeyFile);
    try {
      const response = await axios.post(
          process.env.BACKEND_URL + '/verify-file',
        formData,
        {
          headers: {
            'Content-Type': 'undefined',
            accept: '*/*',
          },
        }
      );
      if (!response) {
        throw new Error('Sign File API unavailable.');
      }
      console.log('calLVerifyFileApi');
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
