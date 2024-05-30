import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VenerdiComponent } from './venerdi.component';

describe('VenerdiComponent', () => {
  let component: VenerdiComponent;
  let fixture: ComponentFixture<VenerdiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VenerdiComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VenerdiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
