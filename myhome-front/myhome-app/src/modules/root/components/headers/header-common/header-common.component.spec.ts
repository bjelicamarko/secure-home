import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderCommonComponent } from './header-common.component';

describe('HeaderCommonComponent', () => {
  let component: HeaderCommonComponent;
  let fixture: ComponentFixture<HeaderCommonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HeaderCommonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderCommonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
