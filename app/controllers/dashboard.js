import Controller from '@ember/controller';
import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';

export default class DashboardController extends Controller {
  @tracked currentQues;
  @tracked currentQuesNo = 0;

  @action
  async viewQuestion() {
    let response = await fetch('http://localhost:8080/PRM_portal/getquestion', {
      credentials: 'include',
    });
    let data = await response.json();

    this.currentQues = data.question;
    this.currentQuesNo = data.no;
  }

  @action
  async sendAns() {
    var answer = document.getElementById('ans').value;
    console.log(answer);
    let response = await fetch('http://localhost:8080/PRM_portal/setanswer', {
      credentials: 'include',
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      mode: 'no-cors',
      body: JSON.stringify({ qnNo: this.currentQuesNo, answer: answer }),
    });
  }
}
