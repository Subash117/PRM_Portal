import Route from '@ember/routing/route';
import { service } from '@ember/service';

export default class ViewquestionsRoute extends Route {
  @service router;
  async beforeModel() {
    let response = await fetch('http://localhost:8080/PRM_portal/getsession', {
      credentials: 'include',
    });
    let data = await response.json();
    if (!data.session || data.pid != 'admin') {
      this.router.transitionTo('admin-login');
    }
  }

  async model(params) {
    let response = await fetch(
      `http://localhost:8080/PRM_portal/getallquestions?id=${params.uid}`
    );
    let data = await response.json();
    data.uid=params.uid;
    return data;
  }
}
