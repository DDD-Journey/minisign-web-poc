export default interface MinisignResponse {
  sessionId: string;
  exitValue: number;
  exitedGraceful: boolean;
  processFeedback: string;
  processError: string;
  createdFiles: Array<string>;
}
