import Controller from '@ember/controller';
import { tracked } from '@glimmer/tracking';
import { A } from '@ember/array';
import { action } from '@ember/object';
import { service } from '@ember/service';

export default class CreateProcessController extends Controller {
  @service router;
  @tracked questionNames = A();
  @tracked questions = A();
  @tracked no = 1;

  @action
  addBox() {
    this.questionNames.pushObject('Question ' + this.no);
    this.no++;
    console.log(this.questionNames);
  }
  @action
  async addQuestions() {
    var name = document.getElementById('name').value;
    var questarr = [];
    for (var i = 0; i < this.questionNames.length; i++) {
      var ques = document.getElementById(this.questionNames[i]).value;
      questarr.push(ques);
    }
    let response = await fetch(
      'http://localhost:8080/PRM_portal/createprocess',
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        mode: 'no-cors',
        body: JSON.stringify({ name: name, questions: questarr }),
      }
    );
    this.router.transitionTo('admin-dashboard');
  }

  @action
  removeBox(question) {
    this.questionNames.removeObject(question);
  }
}
