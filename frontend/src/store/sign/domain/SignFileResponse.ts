export default interface SignFileResponse {
  sessionId: string;
  exitValue: number;
  exitedGraceful: boolean;
  processFeedback: string;
  processError: string;
  createdFiles: Array<string>;
}
