package controllers

import javax.inject.Inject

import model.Vocabulary
import play.api.i18n.Lang
import play.api.mvc._
import service.VocabularyService

class Import @Inject()(cc: ControllerComponents,
                       vocabularyService: VocabularyService) extends AbstractController(cc) {

  def importWord(sourceLanguage: Lang,
                 word: String,
                 targetLanguage: Lang,
                 translation: String) = Action {
    val added = vocabularyService.addVocabulary(
      Vocabulary(sourceLanguage, targetLanguage, word, translation)
    )
    if (added)
      Ok
    else
      Conflict
  }
}

