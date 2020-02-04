podTemplate(
  label: 'docker',
  cloud: 'kubernetes',
  containers: [containerTemplate(image: 'docker', name: 'docker', command: 'cat', ttyEnabled: true)],
  volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')]
  ){
  podTemplate(
    label: 'jenkins-agent',
    containers: [
    containerTemplate(
        name: 'gradle',
        image: 'gradle:5.6-jdk8',
        ttyEnabled: true,
        command: 'cat',
        resourceRequestCpu: '500m',
        resourceLimitCpu: '1500m',
        resourceRequestMemory: '256Mi',
        resourceLimitMemory: '2304Gi',
        envVars: [
           envVar(key: 'DBURL', value: 'jdbc:postgresql://localhost:15432/db'),
           envVar(key: 'NEXUS_URL', value: ''),
           envVar(key: 'GRADLE_DISTRIBUTION_URL', value: ''),
           envVar(key: 'JAVA_OPTS', value: '-Xmx2g -XX:MaxPermSize=256m')
    ]),
    containerTemplate(
        name: 'postgres',
        image: 'postgres:11.5',
        ttyEnabled: true,
        resourceRequestCpu: '500m',
        resourceLimitCpu: '1500m',
        resourceRequestMemory: '256Mi',
        resourceLimitMemory: '2Gi',
        envVars: [
           envVar(key: 'POSTGRES_USER', value: 'dbuser'),
           envVar(key: 'POSTGRES_PASSWORD', value: 'password'),
           envVar(key: 'POSTGRES_DB', value: 'db'),
           envVar(key: 'PGPORT', value: '15432')
    ])
    ],
    volumes: [
        persistentVolumeClaim(mountPath: '/home/gradle', claimName: 'jenkins-agent-gradle-pvc', readOnly: false),
        persistentVolumeClaim(mountPath: '/home/jenkins', claimName: 'jnlp-slave-pvc', readOnly: false)
    ])
  {
    node('jenkins-agent') {
      stage('Preparation') { // for display purposes
        container('gradle') {
          stage('Preparation') {
            // Get some code from a GitHub repository
            git branch: 'build', url: 'https://github.com/vskurikhin/daybook.git', credentialsId: 'github.com-vskurikhin'
          }
        }
      }

      stage('Gradle works') {
        container('gradle') {
          stage('Gradle wrapper') {
              sh 'gradle wrapper $GRADLE_DISTRIBUTION_URL'
          }
          stage('DB update') {
              sh './gradlew clean build update --no-daemon --info $NEXUS_URL'
          }
        }
      }

      stage('Build docker image') {
        container('docker') {
          stage('Build docker image') {
              def locationImage = docker.build('docker.io/vskurikhin/daybook:0.1')
          }
        }
      }
    }
  }
}
