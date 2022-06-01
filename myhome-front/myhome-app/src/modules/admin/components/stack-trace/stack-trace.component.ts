import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConformationDialogComponent } from 'src/modules/shared/components/conformation-dialog/conformation-dialog.component';
import { DialogContent } from 'src/modules/shared/models/DialogContent';

@Component({
  selector: 'app-stack-trace',
  templateUrl: './stack-trace.component.html',
  styleUrls: ['./stack-trace.component.scss']
})
export class StackTraceComponent {

  constructor(public dialogRef: MatDialogRef<ConformationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public dialogContent: DialogContent) { }

}
