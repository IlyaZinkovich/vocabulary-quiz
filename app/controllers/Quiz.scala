package controllers

import javax.inject.Inject

import model.Vocabulary
import play.api.i18n.Lang
import play.api.mvc.{AbstractController, ControllerComponents}
import service.VocabularyService


class Quiz @Inject()(cc: ControllerComponents, vocabulary: VocabularyService) extends AbstractController(cc) {

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
}
