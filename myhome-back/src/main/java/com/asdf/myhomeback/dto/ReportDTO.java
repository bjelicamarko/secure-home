package com.asdf.myhomeback.dto;

import com.asdf.myhomeback.models.DeviceMessage;
import com.asdf.myhomeback.models.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportDTO {
    private String deviceName;
    private String startDate;
    private String endDate;
    private String selectedStatus;
    private int numberOfRegular;
    private int numberOfSuspicious;
    private String content;

    public ReportDTO(String deviceName, String startDate, String endDate,
                     String selectedStatus, List<DeviceMessage> deviceMessages)  {
        this.deviceName = deviceName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.selectedStatus = selectedStatus;
        StringBuilder sb = new StringBuilder();
        LocalDateTime tempTime;
        int i = 1;
        for (DeviceMessage dm: deviceMessages) {
            if (dm.getMessageStatus().equals(MessageStatus.REGULAR))
                numberOfRegular += 1;
            else
                numberOfSuspicious += 1;
            tempTime =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(dm.getTimestamp()),
                            TimeZone.getDefault().toZoneId());
            sb.append(String.format("%s. Timestamp: %s - Status: %s - Message: %s\n", i, tempTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    dm.getMessageStatus().toString(), dm.getMessage()));
            i += 1;
        }
        this.content = sb.toString();
    }

    @Override
    public String toString() {
        return String.format("""
                Device Name: %s
                Start Date: %s
                End date: %s
                Selected Status: %s
                Regular number: %s
                Suspicious number: %s
                Content:
                %s""", this.deviceName, this.startDate, this.endDate, this.selectedStatus, this.numberOfRegular,
                this.numberOfSuspicious, this.content);
    }
}
