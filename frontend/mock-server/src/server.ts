import express from 'express'
import bodyParser from 'body-parser'
import cors from 'cors'
import { upload } from './upload'
import { createKeys } from './createKeys'
import { signFile } from "./signFile";
import { verifyFile } from "./verifyFile";

const app = express()
const port = process.env.PORT || 3000
const corsOptions = {
    origin: '*',
    optionsSuccessStatus: 200,
}
app.use(cors(corsOptions))
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(bodyParser.raw());

app.get('/', (req, res) => {
    res.send("Healthy")
})
app.post('/create', createKeys);
app.post('/sign', signFile);
app.post('/verify', verifyFile);
app.post('/upload', upload)

app.listen(port, () => {
    console.log('\nUpload server running on http://localhost:' + port)
})
