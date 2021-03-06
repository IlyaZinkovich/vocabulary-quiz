package controllers

import javax.inject.Inject

import actors.QuizActor
import akka.actor.ActorSystem
import akka.stream.Materializer
import model.Vocabulary
import play.api.i18n.Lang
import play.api.libs.streams.ActorFlow
import play.api.mvc.{AbstractController, ControllerComponents, WebSocket}
import service.VocabularyService


class Quiz @Inject()(cc: ControllerComponents, vocabulary: VocabularyService)
                    (implicit system: ActorSystem, mat: Materializer)
  extends AbstractController(cc) {

  def quiz(sourceLanguage: Lang, targetLanguage: Lang) = Action {
    vocabulary.findRandomVocabulary(sourceLanguage, targetLanguage) match {
      case Some(v) => Ok(v.word)
      case None => NotFound
    }
  }

  def check(sourceLanguage: Lang, word: String, targetLanguage: Lang, translation: String) = Action {
    val isValid: Boolean = vocabulary.verify(sourceLanguage, word, targetLanguage, translation)
    if (isValid) Ok else NotAcceptable
  }

  def interactiveQuiz(sourceLang: Lang, targetLang: Lang) = WebSocket.accept[String, String] {
    request =>
      ActorFlow.actorRef { out =>
        QuizActor.props(out, sourceLang, targetLang, vocabulary)
      }
  }
}
