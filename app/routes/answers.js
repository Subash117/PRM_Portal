import Route from '@ember/routing/route';

export default class AnswersRoute extends Route {
  queryParams = {
    qnid: { refreshModel: false },
    uid:  { refreshModel: false }
  };

  async model(params) {
    let response = await fetch(
      `http://localhost:8080/PRM_portal/getanswer?qnid=${params.qnid}&uid=${params.uid}`,
      { credentials: 'include' }
    );
    let data = await response.json();
    return data;
  }
}
