/* eslint-disable prettier/prettier */
import { client } from "@/client/axiosClient";

class CertificateService {
    static getAliases() {
        return client({
            url: "api/certificate/getAliases",
            method: "GET",
        })
    }
    static getCertificate(alias) {
        return client({
            url: "api/certificate/getCertificate",
            method: "POST",
            data: alias
        })
    }
}
export default CertificateService;
