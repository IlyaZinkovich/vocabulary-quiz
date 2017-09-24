package actors

import akka.actor.{Actor, ActorRef, Props}
import model.Vocabulary
import play.api.i18n.Lang
import service.VocabularyService

class QuizActor(out: ActorRef,
                sourceLang: Lang,
                targetLang: Lang,
                vocabulary: VocabularyService) extends Actor {

  private var word = ""

  override def preStart(): Unit = sendWord()

  def sendWord() = {
    vocabulary.findRandomVocabulary(sourceLang, targetLang).map { v =>
      out ! s"Please translate '${v.word}'"
      word = v.word
    } getOrElse {
      out ! s"No words for ${sourceLang.code} and ${targetLang.code}"
    }
  }

  def receive: Receive = {
    case translation: String
      if (vocabulary.verify(sourceLang, word, targetLang, translation)) =>
        out ! "Correct!"
        sendWord()
    case _ =>
      out ! "Incorrect, try again!"
  }
}

object QuizActor {
  def props(out: ActorRef,
            sourceLang: Lang,
            targetLang: Lang,
            vocabulary: VocabularyService): Props =
    Props(classOf[QuizActor], out, sourceLang, targetLang, vocabulary)
}