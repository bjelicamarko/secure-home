import { Component, Input, OnInit } from '@angular/core';
import { KeyUsageDTO } from 'src/app/models/KeyUsageDTO';

@Component({
  selector: 'app-key-usage',
  templateUrl: './key-usage.component.html',
  styleUrls: ['./key-usage.component.scss']
})
export class KeyUsageComponent implements OnInit {

  @Input()
  keyUsage!: KeyUsageDTO;

  constructor() {}

  ngOnInit(): void {}

}
