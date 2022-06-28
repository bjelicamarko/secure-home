export interface CertificateDTO {
    issuedTo: string;
    issuedBy: string;
    validFrom: string;
    validTo: string;
    serialNumber: string;
    complexNameSubject: string;
    complexNameIssuer: string;
    publicKeyAlgorithm: string;
    keyUsages: string[];
    extendedKeyUsages: string[];
    isCA: number;
    version: number;
    authorityKeyIdentifier: string;
    subjectKeyIdentifier: string;
}