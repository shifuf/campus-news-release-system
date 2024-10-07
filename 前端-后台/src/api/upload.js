//上传
import {get,post} from "../../utils/request";

export const Upload = (data) => {
    return post("/upload", data);
}