export interface AlarmRuleDTO {
    rulePattern: string,
    deviceName: string
}

export enum AlarmType {
    LOG,
    DEVICE
}

export interface AlarmRuleExtendedDTO extends AlarmRuleDTO {
    id: number,
    rulePattern: string;
    alarmType: AlarmType;
    deviceName: string;
}