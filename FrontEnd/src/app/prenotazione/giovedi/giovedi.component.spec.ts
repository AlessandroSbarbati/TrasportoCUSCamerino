import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GiovediComponent } from './giovedi.component';

describe('GiovediComponent', () => {
  let component: GiovediComponent;
  let fixture: ComponentFixture<GiovediComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GiovediComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GiovediComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
