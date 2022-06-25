import { CertificateDataDTO } from "./CertificateDataDTO";

export interface CsrDTO extends CertificateDataDTO {
    id: number;
    city: string;
}