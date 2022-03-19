import { Request, Response } from "express";
import path from "path";

export function createKeys(req: Request, res: Response) {
    console.log('Got body:', req.body);
    const fileName = 'minisign-keys.zip';
    res.sendFile(path.resolve('static', fileName),  (errors) => {
        if(errors) {
            console.log('Errors:', errors);
        } else {
            console.log('Sent:', fileName);
        }
    })
}
