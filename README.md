# InsuranceSystem

## 🎩 전과자들 커밋 주의사항
1. 본인 컴퓨터 환경과 연관된 설정 파일(iml, xml 등)은 ~~웬만하면~~ 업로드하지 않습니다. 
2. 커밋할 때는 꼭 메시지를 동반하며 메시지 작성 규칙은 아래 규칙을 따릅니다.
3. Commit - Push - Pull Request 과정을 거쳐야 
원본 코드에 본인의 코드를 반영할 수 있습니다.
> ✔️ commit과 push를 하면 본인이 fork한 본인 레포지토리에 반영되며 
pull request를 통해 원본 코드와 비교하여 원본 코드에 본인 코드를 반영해달라는 요청을 할 수 있습니다.
> 요청이 허가되면 Merge를 통해 원본 코드와 합칠 수 있습니다.
4. 작업 중간에 pull을 받고 commit을 한다면 코드 conflict 확률이 증가합니다.
> ✔️ 원본 코드와 내 레포지토리 코드는 실시간 연동이 불가합니다.
> 절대 작업 중간에 pull을 받지 않고 작업이 끝난 후 pull을 받아 최신화하도록 합니다.

## 🎩 전과자들 커밋 메시지 작성 규칙

### [type:] Content of commit message

###ex) feat: Add login api
> type은 아래 타입 목록에 있는 타입 중 하나를 소문자로 작성하고  
> 이후에 붙는 내용은 동사로 시작합니다. (앞글자 대문자)  
아래 명시된 타입 목록을 꼭 확인하고 작성해주세요!     
> 타입 목록은 개발자들 사이에서 자주 사용하는 목록을 따왔습니다.

Type
- feat : 새로운 기능 추가, 기존의 기능을 요구 사항에 맞추어 수정
- fix : 기능에 대한 버그 수정
- build : 빌드 관련 수정
- chore : 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore
- ci : CI 관련 설정 수정
- docs : 문서(주석) 수정
- style : 코드 스타일, 포맷팅에 대한 수정
- refactor : 기능의 변화가 아닌 코드 리팩터링 ex) 변수 이름 변경
- test : 테스트 코드 추가/수정
- release : 버전 릴리즈