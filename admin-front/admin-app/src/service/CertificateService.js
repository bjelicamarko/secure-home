/* eslint-disable prettier/prettier */
import { client } from "@/client/axiosClient";

class CertificateService {
    static getAliases() {
        return client({
            url: "api/certificates/getAliases",
            method: "GET",
        })
    }
    static getCertificate(alias) {
        return client({
            url: "api/certificates/getCertificate/" + alias,
            method: "POST",
            data: alias
        })
    }
    
    static createCertificate(certDTO) {
        return client({
            url: "api/certificates",
            method: "POST",
            data: certDTO
        })
    }
    
}
export default CertificateService;
