import Route from '@ember/routing/route';

export default class ResultsRoute extends Route {
    async model(params)
    {
        console.log(params.uid);
        let response=await fetch(`http://localhost:8080/PRM_portal/getresult?uid=${params.uid}`,{credentials:'include'});
        let data=await response.json();
        return data;
    }
}
