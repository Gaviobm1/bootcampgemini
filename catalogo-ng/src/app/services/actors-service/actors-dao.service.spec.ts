import { TestBed } from '@angular/core/testing';

import { ActorsDAOService } from './actors-dao.service';

describe('ActorsDaoService', () => {
  let service: ActorsDAOService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ActorsDAOService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
