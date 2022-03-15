import express from 'express'
import cors from 'cors'
import { upload } from './upload'


const app = express()
const port = process.env.PORT || 3000

app.get('/', (req, res) => {
    res.send("Healthy")
})

const corsOptions = {
    origin: '*',
    optionsSuccessStatus: 200,
}
app.use(cors(corsOptions))
app.post('/upload', upload)

app.listen(port, () => {
    console.log('\nUpload server running on http://localhost:' + port)
})
