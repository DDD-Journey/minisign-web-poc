export function downloadFile(data: any, name: string) {
  const FILE = window.URL.createObjectURL(new Blob([data]));
  const link = document.createElement('a');
  link.href = FILE;
  link.setAttribute('download', name);
  document.body.appendChild(link);
  link.click();
}
