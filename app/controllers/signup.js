import Controller from '@ember/controller';
import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';

export default class SignupController extends Controller {
  queryParams = ['id'];
  @tracked warn = '';
  @tracked disabled = 'hidden';

  @action
  checkPass(event) {
    var pass1 = document.getElementById('pass1').value;
    var pass2 = event.target.value;
    if (pass1 != pass2) {
      console.log('Not Equal');
      this.warn = "Passwords Don't match";
      this.disabled = 'hidden';
    } else {
      this.warn = '';
      this.disabled = 'submit';
    }
    if (pass2 == '') {
      this.warn = '';
    }
  }
}
