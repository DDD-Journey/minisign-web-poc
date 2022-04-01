import axios from "axios";

export default class DownloadExchange {
    static async callDownloadSignatureFileApi(
        sessionId: string
    ): Promise<any | undefined> {
        try {
            const response = await axios.get(
                process.env.VUE_APP_BACKEND_URL + '/download-files/' + sessionId,
                {
                    headers: {
                        accept: 'application/octet-stream',
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
