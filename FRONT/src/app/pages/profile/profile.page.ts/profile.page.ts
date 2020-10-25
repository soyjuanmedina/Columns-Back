import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../../../_services/token-storage.service';

@Component({
  selector: 'profile-page',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss']
})
export class ProfilePage implements OnInit {


  currentUser: any;

  constructor(private token: TokenStorageService) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
  }

}
