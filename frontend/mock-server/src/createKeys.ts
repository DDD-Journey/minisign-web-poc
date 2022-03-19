import { Request, Response } from "express";
import path from "path";
import { spawn } from 'child_process';

export function createKeys(req: Request, res: Response) {
    console.log('Got body:', req.body);

    // https://dev.to/masanori_msl/node-js-typescript-windows-play-with-child-process-114e
    const execProcess = spawn('minisign', ['-G'])
    execProcess.stdin.setDefaultEncoding('utf8')

    console.log('spawn');
    console.log(execProcess.spawnfile);
    execProcess.on('spawn', () => {
        console.log('spawn on spawn');
    });
    execProcess.stdout.on('data', (data) => {
        data = data.toString();
        // https://stackoverflow.com/questions/40576439/node-js-input-password-to-a-bin-file-being-run-via-spawn
        // first time pw
        execProcess.stdin.write('t');
        execProcess.stdin.write('e');
        execProcess.stdin.write('s');
        execProcess.stdin.write('t');
        execProcess.stdin.write("\n");
        // second time pw
        execProcess.stdin.write('t');
        execProcess.stdin.write('e');
        execProcess.stdin.write('s');
        execProcess.stdin.write('t');
        execProcess.stdin.write("\n");
        // }
        console.log(`spawn stdout: ${data}`);
    })
    execProcess.stderr.on('data', (data) => {
        console.log(`spawn on error ${data}`);
    });
    execProcess.on('exit', (code, signal) => {
        console.log(`spawn on exit code: ${code} signal: ${signal}`);
    });
    execProcess.on('close', (code: number, args: any[])=> {
        console.log(`spawn on close code: ${code} args: ${args}`);
    });

    const fileName = 'minisign-keys.zip';
    res.sendFile(path.resolve('static', fileName),  (errors) => {
        if(errors) {
            console.log('Errors:', errors);
        } else {
            console.log('Sent:', fileName);
        }
    })
}
