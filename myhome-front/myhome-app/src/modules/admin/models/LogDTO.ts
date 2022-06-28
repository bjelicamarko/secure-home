export interface LogDTO {
    dateTime: number,
    logLevel: string;
    loggerName: string;
    logMessage: string;
    stackTrace: string;
}