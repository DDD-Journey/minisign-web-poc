import { Request, Response } from 'express'
import { IncomingForm } from 'formidable'

export function upload(req: Request, res: Response) {
    const form = new IncomingForm();
    form.on('file', (field, file) => {
        // Do something with the file
        // e.g. save it to the database
        // you can access it using file.path
        console.log('file', file.originalFilename)
    });
    form.on('end', () => {
        res.json()
    });
    form.parse(req, (_, fields, files) => {
        console.log('\n-----------')
        console.log('Fields', fields)
        console.log('Received:', Object.keys(files))
        console.log('File Name: ' + JSON.stringify(files))
    })
}
