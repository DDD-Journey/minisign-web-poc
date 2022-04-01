import MinisignResponse from '@/store/share/domain/MinisignResponse';
import axios from 'axios';

export default class CreateKeyPairExchange {
  static async callCreateKeyPairApi(
    password: string,
    fileName: string
  ): Promise<MinisignResponse | undefined> {
    try {
      const response = await axios.post(
        process.env.VUE_APP_BACKEND_URL + '/create-keys',
        {
          password,
          fileName,
        },  {
            headers: {
              'Content-Type': 'application/json',
              accept: 'application/json',
            },
          }

      );
      if (!response) {
        throw new Error('Create key pair API unavailable.');
      }
      console.log('callCreateKeyPairApi');
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
