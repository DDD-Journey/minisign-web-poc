import { Request, Response } from "express";
import path from "path";
import { IncomingForm } from "formidable";

export function signFile(req: Request, res: Response) {
    console.log('Got body:', req.body);

    const form = new IncomingForm();
    form.on('file', (field, file) => {
        console.log('file', file.originalFilename)
    });
    // form.on('end', () => {
    //     res.json()
    // });
    form.on('end', () => {
        res.json()
    });
    form.parse(req, (_, fields, files) => {
        console.log('\n-----------')
        console.log('Fields', fields)
        console.log('Received:', Object.keys(files))
        console.log('File Name: ' + JSON.stringify(files))
    })

    const fileName = 'minisign-signed.zip';
    res.sendFile(path.resolve('static', fileName),  (errors) => {
        if(errors) {
            console.log('Errors:', errors);
        } else {
            console.log('Sent:', fileName);
        }
    })
}
