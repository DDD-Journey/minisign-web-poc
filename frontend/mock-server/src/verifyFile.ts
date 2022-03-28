import { Request, Response } from "express";

export function verifyFile(req: Request, res: Response) {
    console.log('Got body:', req.body);
    const responseBody = {
        message: 'Signature and comment signature verified\nTrusted comment: timestamp:1647708718   file:minisign-text.txt  hashed'
    }
    res.send(responseBody);
}
